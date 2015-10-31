// TODO(dev-name):Implement SomeClass
class SomeClass {

    // todo: Implement someMethod
    func someMethod() {
        // ToDo Remove comment after implementing someMethod
    }

    // this is an invalid comment because it contains TODO:todo Todo: words



    /* Bug: return positive integer
     */
    func getPositiveNumber() -> Int {
        // TODO: Valid single line todo comment

        /* multi-line
         comment */

        return -1
    }

    // TODO(aditya): this is a valid TODO syntax


    // TODO : invalid

    // TODO(): invalid

    /// TODO: documentation comments should not have tasks to be done
}
