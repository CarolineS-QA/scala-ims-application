package com.qa.ims

import com.qa.ims.configuration.MongoConfiguration
import com.qa.ims.service.InputService

import scala.annotation.tailrec
import scala.io.StdIn.readLine


object IMS {


  def main(args: Array[String]): Unit = {

    // Configures connection to database
    MongoConfiguration

    // Implements interface and calls CRUD functions
    InputService




  }
}
