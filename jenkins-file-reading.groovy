pipelineJob('read_data_from_a_file') {
  
def fileContent = new File("/var/lib/jenkins/workspace/info.txt").text

    definition {
        cps {
            script(''' 
  
    //def fileContent = new File("/var/lib/jenkins/workspace/info.txt").text
    println fileContent

                ''')

    parameters {
        textParam('EMAIL_TEMPLATE', fileContent, 'Email notification template')
    }
            sandbox(false)
        }
    }
}


