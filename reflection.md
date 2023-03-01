# Reflection on implementing regular expressions of a DSL

## Which operators were easiest to implement and why?
The first couple of operators like || and ~ were definitely the easiest because they just consist of calling the corresponding regular language object (Union and Concat respectively). 

## Which operators were most difficult to implement and why?
toDFA was difficult because we had to wrap our hands around this new idea of given instances and using clauses, something we haven't discussed previously. Looking at the example and seeing how it might apply to our case was not the easiest translation. 

## Comment on the design of this internal DSL

Write a few brief paragraphs that discuss:

- What works about this design? (For example, what things seem easy and
  natural to say, using the DSL?)
- What doesn't work about this design? (For example, what things seem
  cumbersome to say?)
- Think of a syntactic change that might make the language better. How would
  you implement it _or_ what features of Scala would prevent you from
  implementing it? (You don't have to write code for this part. You could say
  "I would use extension to..." or "Scala's rules for valid
  identifiers prevent...")

Extending regular languages by introducing shorthand notation for the operators definitely improved readability by leaps and bounds; one would much rather see re1 ~ re2 than Concat(re1, re2) in my opinion. Additionally, all the implicit conversions really help with making things see easy from the surface level; for example, if I want to create a DFA from a regular language, I can do so by just passing in a regular language into a function that would use a DFA and the implicit conversion would take care of it for me. 

However, the fact that we can't exactly do the same with operators such as * seem frustrating. I feel like if there was a way to make it such that it was possible to call operators without <> around it, it would make things look cleaner. I believe that this is not possible, though, because * is already defined to be something that is used in Scala. I wonder if you would be able to override it somehow so that it works when * is being called next to a regex, though. 
