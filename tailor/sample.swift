class hello {
  var x: Int
}

class Underscore_urgh {
  var hi: String
}

class ThisShouldBeGood {
  var bloh : Int
  var boo : String
}

enum Enumeration {
  case invalid
  case Invalid_name
  case invalid_name
  case ValidName
  case Validname
}

enum Barcode {
  case UPCA(Int, Int, Int, Int)
  case QRCode(String)
}

enum ASCIIControlCharacter: Character {
    case Tab = "\t"
    case LineFeed = "\n"
    case CarriageReturn = "\r"
}

enum invalidEnum {
  case Nothing
}

enum invalid_enum {
  case Really
}

enum Invalid_enum {
  case Matters
}

enum ValidEnumName {
  case ToMe
}

struct ValidStructForCar: Vehicle {
    let numberOfWheels = 4
}

struct invalidStructForCar: Vehicle {
    let numberOfWheels = 4
}

struct invalid_StructForCar: Vehicle {
    let numberOfWheels = 4
}

protocol ValidProtocol {
    // protocol definition goes here
}

protocol invalidProtocol {
    // protocol definition goes here
}

protocol Invalid_Protocol {
    // protocol definition goes here
}
