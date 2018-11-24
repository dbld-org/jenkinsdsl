jenkins.model.Jenkins.theInstance.getProjects().each { job ->
    if (!job.name.contains('dbld_admn')) {
        println job.name
        job.delete()
    }
}
