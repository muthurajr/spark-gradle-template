package com.muthurajr.sparkgradletemplate.spark1.service

import com.muthurajr.sparkgradletemplate.spark1.data.{Customer, Issue}
import com.muthurajr.sparkgradletemplate.spark1.encoder.ModelEncoder._
import org.apache.spark.sql.{Dataset, SparkSession}


object InputReaderService {

  def readCustomer(spark: SparkSession, inputLocation: String): Dataset[Customer] = {
    spark.read.load(inputLocation).as[Customer]
  }

  def readIssue(spark: SparkSession, inputLocation: String): Dataset[Issue] = {
    spark.read.load(inputLocation).as[Issue]
  }

}
