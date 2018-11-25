jenkins.model.Jenkins.theInstance.getAllItems().each { job -> 
      if (!job.name.contains('dbld_admn')) {
        println job.fullName
        job.delete()
    }
}
