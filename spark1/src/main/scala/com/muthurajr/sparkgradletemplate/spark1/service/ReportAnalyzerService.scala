package com.muthurajr.sparkgradletemplate.spark1.service

import com.muthurajr.sparkgradletemplate.spark1.data.{Customer, Issue, IssueReport}
import com.muthurajr.sparkgradletemplate.spark1.function.ReportAnalyzer
import com.muthurajr.sparkgradletemplate.spark1.tempdata.GroupedIssue
import com.muthurajr.sparkgradletemplate.spark1.encoder.ModelEncoder._
import org.apache.spark.sql.functions.{collect_list, struct}
import org.apache.spark.sql.{Dataset, SparkSession}

object ReportAnalyzerService {


  def process(spark: SparkSession,
              customer: Dataset[Customer],
              issues: Dataset[Issue]): Dataset[IssueReport] = {
    val groupedIssues = issues.groupBy("customer_id")
      .agg(collect_list(
        struct(
          "customer_id",
          "issue_id",
          "name",
          "description",
          "report_date",
          "status"
        )
      ).as("issues"))
      .as[GroupedIssue]
    customer.joinWith(groupedIssues, customer("customer_id") === groupedIssues("customer_id"), "inner")
      .map { case (a, v) => ReportAnalyzer.create(a, Some(v.issues)) }
  }

}
