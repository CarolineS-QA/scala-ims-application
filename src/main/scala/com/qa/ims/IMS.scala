package com.qa.ims

import com.qa.ims.configuration.MongoConfiguration
import com.qa.ims.service.InputService


object IMS {


  def main(args: Array[String]): Unit = {

    // Configures connection to database
    MongoConfiguration

    // Implements interface and calls CRUD functions
    InputService

  }
}
