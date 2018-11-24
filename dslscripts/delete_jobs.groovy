jenkins.model.Jenkins.theInstance.getProjects().each { job ->
    if (!job.name.contains('delete_jobs')) {
        println job.name
        job.delete()
    }
}
