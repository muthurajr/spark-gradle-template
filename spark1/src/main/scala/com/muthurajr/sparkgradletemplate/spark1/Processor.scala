package com.muthurajr.sparkgradletemplate.spark1

import com.muthurajr.sparkgradletemplate.spark1.service.{InputReaderService, OutputWriterService, ReportAnalyzerService}
import org.apache.spark.sql.SparkSession

object Processor extends App {

  case class Arguments(customerPath: String, issuesPath: String, reportPath: String)

  val parser = new scopt.OptionParser[Arguments]("Customer Report") {
    opt[String]('c', "customerPath").
      required().valueName("").action((value, arguments) => arguments.copy(customerPath = value))
    opt[String]('i', "issuesPath").
      required().valueName("").action((value, arguments) => arguments.copy(issuesPath = value))
    opt[String]('r', "reportPath").
      required().valueName("").action((value, arguments) => arguments.copy(reportPath = value))
  }

  def run(arguments: Arguments): Unit = {
    val spark = SparkSession.builder()
      .appName("Customer Report")
      .getOrCreate()

    //read
    val customer = InputReaderService.readCustomer(spark, arguments.customerPath)
    val issues = InputReaderService.readIssue(spark, arguments.issuesPath)

    //process
    val report = ReportAnalyzerService.process(spark, customer, issues)

    //write
    OutputWriterService.write(report, arguments.reportPath)
  }

  parser.parse(args, Arguments("", "", "")) match {
    case Some(arguments) => run(arguments)
    case None =>
  }
}
