package com.muthurajr.sparkgradletemplate.spark1.service

import java.sql.Date

import com.holdenkarau.spark.testing.DatasetSuiteBase
import com.muthurajr.sparkgradletemplate.spark1.data.{Customer, Issue}
import com.muthurajr.sparkgradletemplate.spark1.encoder.ModelEncoder._
import org.apache.spark.sql.SaveMode
import org.junit.rules.TemporaryFolder
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfterAll, FunSpec}

@RunWith(classOf[JUnitRunner])
class InputReaderServiceTest extends FunSpec with DatasetSuiteBase with BeforeAndAfterAll {

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
    it("read issue") {
      //given
      val issues = spark.createDataset(Array[Issue](Issue(1, 1, "issue1", "description1", Date.valueOf("2019-01-01"), "created")))
      val path = s"${directory}/sc1/issue/"
      issues.write.mode(SaveMode.Overwrite).parquet(path)

      //when
      val actualResult = InputReaderService.readIssue(spark, path)

      //then
      assertDatasetEquals(issues, actualResult)
    }
    it("read customer") {
      //given
      val customer = spark.createDataset(Array[Customer](Customer(1, "first1", "last1")))
      val path = s"${directory}/sc1/customer/"
      customer.write.mode(SaveMode.Overwrite).parquet(path)

      //when
      val actualResult = InputReaderService.readCustomer(spark, path)

      //then
      assertDatasetEquals(customer, actualResult)

    }
  }


}
