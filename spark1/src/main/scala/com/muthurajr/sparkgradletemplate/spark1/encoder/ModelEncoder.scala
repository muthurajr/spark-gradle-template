package com.muthurajr.sparkgradletemplate.spark1.encoder

import com.muthurajr.sparkgradletemplate.spark1.data.{Customer, Issue, IssueReport}
import com.muthurajr.sparkgradletemplate.spark1.tempdata.GroupedIssue
import org.apache.spark.sql.catalyst.encoders.ExpressionEncoder

object ModelEncoder {

  implicit val GROUPED_ISSUE_ENCODER = ExpressionEncoder[GroupedIssue]
  implicit val ISSUE_ENCODER = ExpressionEncoder[Issue]
  implicit val CUSTOMER_ENCODER = ExpressionEncoder[Customer]
  implicit val ISSUE_REPORT_ENCODER = ExpressionEncoder[IssueReport]
}
