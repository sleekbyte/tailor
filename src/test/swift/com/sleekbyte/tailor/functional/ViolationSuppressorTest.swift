// Class examples

// tailor:off
class lowerCamelCase {
  var x: Int
}

class Snake_Case {
  var x: String
}
// tailor:on

// something tailor:off on
class UpperCamelCase {
  var foo: Int
  var bar: String
}

class UpperCamelCase: SuperClass {
  var foo: Int
  var bar: String
}

class UpperCamelCase: superClass { // tailor:disable
  var foo: Int
  var bar: String
}

class Snake_Case {
  var x: String
}

// tailor:on
class UppCamelCase: superClass {
  var foo: Int
  var bar: String
}
// tailor:off
