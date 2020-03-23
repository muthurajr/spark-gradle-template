package com.muthurajr.sparkgradletemplate.spark1.function

import com.muthurajr.sparkgradletemplate.spark1.data.{Customer, Issue, IssueReport}

import scala.collection.mutable

object ReportAnalyzer {

  //Merge the customer and issue data, and generate the report
  def create(customer: Customer, issues: Option[Array[Issue]]): IssueReport = {
    val issueCount = mutable.Map[String, Int]();
    if (issues.isDefined) {
      issues.get.foreach(issue => {
        val newCount = issueCount.getOrElse(issue.status, 0) + 1
        issueCount.put(issue.status, newCount)
      })
    }
    IssueReport(customer.customer_id, issueCount.toMap)
  }

}
