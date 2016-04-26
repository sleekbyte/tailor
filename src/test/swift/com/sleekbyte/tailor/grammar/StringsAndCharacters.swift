let someString = "Some string literal value"

var emptyString = ""               // empty string literal
var anotherEmptyString = String()  // initializer syntax

if emptyString.isEmpty {
    print("Nothing to see here")
}

var variableString = "Horse"
variableString += " and carriage"
// variableString is now "Horse and carriage"

let constantString = "Highlander"
constantString += " and another Highlander"

let exclamationMark: Character = "!"

let string1 = "hello"
let string2 = " there"
var welcome = string1 + string2

var instruction = "look over"
instruction += string2

let exclamationMark: Character = "!"
welcome.append(exclamationMark)

let multiplier = 3
let message = "\(multiplier) times 2.5 is \(Double(multiplier) * 2.5)"

// let eAcute: Character = "\u{E9}"                         // é
// let combinedEAcute: Character = "\u{65}\u{301}"          // e followed by ́

let greeting = "Guten Tag"
greeting[greeting.startIndex]
// G
greeting[greeting.endIndex.predecessor()]
// g
greeting[greeting.startIndex.successor()]
// u
let index = advance(greeting.startIndex, 7)
greeting[index]
// a
greeting[greeting.endIndex] // error
greeting.endIndex.successor() // error

for index in indices(greeting) {
    print("\(greeting[index]) ")
}
print("\n")

var welcome = "hello"
welcome.insert("!", atIndex: welcome.endIndex)

welcome.splice(" there".characters, atIndex: welcome.endIndex.predecessor())

welcome.removeAtIndex(welcome.endIndex.predecessor())

let range = advance(welcome.endIndex, -6)..<welcome.endIndex
welcome.removeRange(range)

for codeUnit in dogString.utf8 {
    print("\(codeUnit) ", appendNewline: false)
}
print("")

for codeUnit in dogString.utf16 {
    print("\(codeUnit) ", appendNewline: false)
}
print("")

for scalar in dogString.unicodeScalars {
    print("\(scalar.value) ", appendNewline: false)
}
print("")

private let FAIcons = ["\u{f26e}", "\u{f0426}", "\u{f}", "\u{f0372992}"]

let args = "[\(args.map(toYAML).joinWithSeparator(","))]"
let testMsg = " (after \(st.successfulTestCount.successor()) test"

let description = "\(firstVariable), \(secondVariable), \(thirdVariable), \(fourthVariable), \(fifthVariable)"

return (components.map { "\($0)=\($1)" } as [String]).joinWithSeparator("&")

var Protocol = "just a variable called Protocol"
