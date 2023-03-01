package machines

import regex._
import dfa._

// Conversions
given Conversion[Char, RegularLanguage] = Character(_)
given Conversion[String, RegularLanguage] = strToRegex(_)
given Conversion[RegularLanguage, DFA] = re => regexToDFA(re, re.chars)

// Helper function that takes a string and returns 
// what it would look like as a regex.
def strToRegex(str: String) : RegularLanguage = {
    var charList = str.toList
    charList.foldRight(Concat(Epsilon, Epsilon))(Concat(_, _))
}

extension (re1: RegularLanguage)
    def || (re2: RegularLanguage) = Union(re1, re2)

    def ~ (re2: RegularLanguage) = Concat(re1, re2)

    def <*> = Star(re1)

    def <+> = Concat(re1, Star(re1))

    def apply(n: Int) : RegularLanguage = {
        if (n == 0) then Epsilon else Concat(re1, apply(n-1))
    }
    
    def toDFA(using alphabet: Set[Char]): DFA = regexToDFA(re1, alphabet)
    
    // Takes a regular language and extracts the alphabet from it, 
    // returned as a set of chars
    def chars: Set[Char] = re1 match 
        case Empty => Set[Char]()
        case Epsilon => Set[Char]()
        case Character(c) => Set[Char](c) 
        case Union(r1:RegularLanguage, r2:RegularLanguage) => r1.chars ++ r2.chars
        case Concat(r1:RegularLanguage, r2:RegularLanguage) => r1.chars ++ r2.chars
        case Star(r:RegularLanguage) => r.chars