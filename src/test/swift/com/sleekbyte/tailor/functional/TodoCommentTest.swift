// TODO:Implement SomeClass
class SomeClass {

    // todo: Implement someMethod
    func someMethod() {
        // ToDo Remove comment after implementing someMethod
    }

    // this is an invalid comment because it contains TODO:todo Todo: words
    /* this is also an invalid comment since it has TODO:todo:
    Todo: words */

    /* tOdo fix bug: return positive integer
     */
    func getPositiveNumber() -> Int {
        // TODO: Valid single line todo comment

        /* TODO: Valid multiline todo
         comment */

        return -1
    }

    // TODO(aditya): this is a valid TODO syntax
    /* TODO(aditya): this too is a valid multiline TODO syntax */

    // TODO : invalid

    // TODO(): invalid

    /* TODO(): invalid */

    /*
    TODO(aditya): valid
    */

    /*
    TODO(): invalid
    */

    /*
    TODO: valid
    */

}
