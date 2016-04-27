struct TimesTable {
    let multiplier: Int
    subscript(index: Int) -> Int {
        return multiplier * index
    }
}
let threeTimesTable = TimesTable(multiplier: 3)
print("six times three is \(threeTimesTable[6])")

var numberOfLegs = ["spider": 8, "ant": 6, "cat": 4]
numberOfLegs["bird"] = 2

struct Matrix {
    let rows: Int, columns: Int
    var grid: [Double]
    init(rows: Int, columns: Int) {
        self.rows = rows
        self.columns = columns
        grid = Array(count: rows * columns, repeatedValue: 0.0)
    }
    func indexIsValidForRow(row: Int, column: Int) -> Bool {
        return row >= 0 && row < rows && column >= 0 && column < columns
    }
    subscript(row: Int, column: Int) -> Double {
        get {
            assert(indexIsValidForRow(row, column: column), "Index out of range")
            return grid[(row * columns) + column]
        }
        set {
            assert(indexIsValidForRow(row, column: column), "Index out of range")
            grid[(row * columns) + column] = newValue
        }
    }
}

matrix[0, 1] = 1.5
matrix[1, 0] = 3.2

func indexIsValidForRow(row: Int, column: Int) -> Bool {
    return row >= 0 && row < rows && column >= 0 && column < columns
}

struct Celsius {
    var temperatureInCelsius: Double = 4
    var temperatureInKelvin: Double = 5

    func doSomething() {
        self[temperatureInCelsius, temperatureInKelvin]
    }

    subscript(celsius: Double, farenheit: Double) -> Int {
        get {
            return 2
        }
        set(newValue) {
            // perform a suitable setting action here
        }
    }
}

extension SystemError {
    public static func description(for errorNumber: Int32) -> String {
        return String(cString: strerror(errorNumber))
    }
}

extension SystemError: CustomStringConvertible {
    public var description: String {
        return SystemError.description(for: errorNumber)
    }
}
