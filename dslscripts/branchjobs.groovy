String basePath = 'multipleBranches'

folder(basePath) {
    description 'This example shows how to create a set of jobs for each github branch, each in its own folder.'
}

def project = 'qualica/flexifin-documents'
def branchApi = new URL("https://api.github.com/repos/${project}/branches")
def branches = new groovy.json.JsonSlurper().parse(branchApi.newReader())
branches.each {
    def branchName = it.name
    def jobName = "${project}-${branchName}".replaceAll('/','-')
    job("${basePath}/${jobName}") {
        scm {
              remote {
                github("${project}/${branchName}", 'ssh')
                credentials('jenkins-github')
                }
            // git("git://github.com/${project}.git", branchName)
        }
        steps {
            maven("test -Dproject.name=${project}/${branchName}")
        }
    }
}


          
