func onePlusTwo() -> Int {
  return 1 + 2
}

func onePlusTwo()-> Int {
  return 1 + 2
}

func onePlusTwo() ->Int {
  return 1 + 2
}

func onePlusTwo()  ->  Int {
  return 1 + 2
}

names.map() {
  (name)-> Int in
  return 1
}

names.map() {
  (name) ->  Int in
  return 1
}

names.map() {
  (name: String)  -> Int in
  return 1
}

names.map() {
  (name: String) ->Int in
  return 1
}

func map(a: (String)-> String) {
  // do something
}

func map(a: (String) ->  String) {
  // do something
}

func something() -> (Int, Int) throws  -> (Int) {
  // do something
}

func something() -> (Int, Int) ->(Int) {
  // do something
}

struct TimesTable {
    let multiplier: Int

    subscript(index: Int) -> Int {
        return multiplier * index
    }

    subscript(index: Int)-> Int {
        return multiplier * index
    }

    subscript(index: Int) ->  Int {
        return multiplier * index
    }
}
