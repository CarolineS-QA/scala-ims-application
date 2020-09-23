package com.qa.ims

import com.qa.ims.configuration.MongoConfiguration
import com.qa.ims.view.InputView


object IMS {


  def main(args: Array[String]): Unit = {

    // Configures connection to database
    MongoConfiguration

    // Implements interface and calls CRUD functions
    InputView




  }
}
