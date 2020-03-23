package com.muthurajr.sparkgradletemplate.spark1.service

import com.muthurajr.sparkgradletemplate.spark1.data.IssueReport
import org.apache.spark.sql.{Dataset, SaveMode}

object OutputWriterService {

  def write(report: Dataset[IssueReport], outputLocation: String) = {
    report.write.mode(SaveMode.Overwrite).parquet(outputLocation)
  }

}
