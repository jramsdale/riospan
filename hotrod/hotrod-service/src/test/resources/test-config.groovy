/*
 * Configuration file for integration test cases
 */
ITRioSpanDeployTest {
    groups = "RioSpanTest"
    numCybernodes = 1
    numMonitors = 1
    //numLookups = 1
    opstring = '../src/main/opstring/org.rioproject.substrates.riospan.groovy'
    autoDeploy = true
    //harvest = true
}

