class Rectangle : Shape
{
    let length:Double
    let breadth:Double

    init(length:Double, breadth:Double)
    {
        //store the row and column of the square in the grid
        self.length = length
        self.breadth = breadth
    }

    func calculateArea() -> Double
    {
        return length * breadth
    }

    func printAreaIfStatement()
    {
        var area = calculateArea()
        if area == Double.infinity {
            println("something went wrong!")
            return
        }

        if area == 0
        {
            println ("Area is zero")
        }

        else if area == 1
        {
            println ("Area is 1")
        }

        else
        {
            println ("Area is \(area)")
        }
    }

    func printAreaSwitchStatement() {
        var area = calculateArea()
        if area == Double.infinity {
            println("something went wrong!")
            return
        }

        switch area
        {
            case 0:
                println ("Area is zero")
            case 1:
                println ("Area is one sq units")
            default:
                println ("Area is \(area) sq units")
        }
    }

    func funWithLoops() {
        let lengthArray = [0,1,2,3,4]
        let breadthArray = [0,1,2,3,4]

        for var x = 0; x < lengthArray.count; x+=1 {
            println(x)
        }

        for breadth in breadthArray {
            println(breadth)
        }

        while false {
            println("Will never be executed")
        }

        repeat {
            // infinite loop
        } while true
    }

    func moreFunWithLoops()
    {
        let lengthArray = [0,1,2,3,4]
        let breadthArray = [0,1,2,3,4]

        for var x = 0; x < lengthArray.count; x+=1
        {
            println(x)
        }

        for breadth in breadthArray
        {
            println(breadth)
        }

        while false
        {
            println("Will never be executed")
        }

        repeat
        {
            // infinite loop
        } while true
    }

    func multiLineFunction(arg1: String,
                           arg2: String) {
        // do nothing.
   }

   class Shape
   {

   }
}
