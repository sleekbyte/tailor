func printAreaSwitchStatement() {
        var area = calculateArea()
        if area == Double.infinity {
            println("something went wrong!")
            return }

        switch area {
            case 0:
                println ("Area is zero")
            case 1:
                println ("Area is one sq units")
            default:
                println ("Area is \(area) sq units") }
}
