package com.muthurajr.sparkgradletemplate.spark1.data

import java.sql.Date

case class Issue(customer_id: Int, issue_id: Int, name: String, description: String, report_date: Date, status: String);
