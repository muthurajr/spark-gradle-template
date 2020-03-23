package com.muthurajr.sparkgradletemplate.spark1.service

import com.holdenkarau.spark.testing.DatasetSuiteBase
import com.muthurajr.sparkgradletemplate.spark1.data.IssueReport
import com.muthurajr.sparkgradletemplate.spark1.encoder.ModelEncoder._
import org.junit.rules.TemporaryFolder
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfterAll, FunSpec}

@RunWith(classOf[JUnitRunner])
class OutputWriterServiceTest extends FunSpec with DatasetSuiteBase with BeforeAndAfterAll {

  var tempFolder: TemporaryFolder = _
  var directory: String = _

  override def beforeAll() {
    super.beforeAll()
    tempFolder = new TemporaryFolder
    tempFolder.create()
    directory = tempFolder.getRoot.getAbsolutePath
  }

  override def afterAll() {
    super.afterAll()
    tempFolder.delete()
  }

  describe("Valid scenario with ") {
    it("write data") {
      //given
      val path = s"${directory}/sc1/report/"
      val expected = spark.createDataset(Array[IssueReport](IssueReport(1, Map("created" -> 2, "solved" -> 1)),
        IssueReport(2, Map("created" -> 1))
      ))

      //when
      OutputWriterService.write(expected, path)

      //then
      val actual = spark.read.parquet(path).as[IssueReport]
      assertDatasetEquals(expected.sort("customer_id"), actual.sort("customer_id"))
    }
  }


}
