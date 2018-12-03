def owner = 'qualica'


repoApi = new URL("https://api.github.com/orgs/${owner}/repos?per_page=200")
repos =
        new groovy.json.JsonSlurper()
                .parse(repoApi.newReader(
                requestProperties: [Authorization: "token f19bb0c7f0a7b4305c0d802aed8bc975ee515c90"]))


repos.findAll { it.name.startsWith('flexifin-') }.each {
    def repo = it.name
    def project = it.full_name
    // def branchApi = new URL(it.branches_url)

    String basePath = "${repo}-branches"

    folder(basePath) {
        description "${repo} branch builds"
    }


    def branchApi = new URL("https://api.github.com/repos/${project}/branches")
    def branches =
            new groovy.json.JsonSlurper()
                    .parse(branchApi.newReader(
                    requestProperties: [Authorization: "token f19bb0c7f0a7b4305c0d802aed8bc975ee515c90"]))
    branches.each {
        def branchName = it.name
        def jobName = "${repo}-${branchName}".replaceAll('/', '-')
        pipelineJob("${basePath}/${jobName}") {
            definition{
                cpsScm{
                    lightweight(lightweight = true)
                    scm {
                        git() {
                            branch("${branchName}")
                            remote {
                                github("${project}", 'ssh')
                                credentials('jenkins-github')
                            }
                        }
                        triggers {

                            scm('H/20 * * * *')
                        }
                    }
                }
            }
        }
    }

}
