package com.jonas.groovy

class GroovyClass {
	public static void main(String[] args) {
		def result = new GroovyClass().testJenkins()
		println(result)
	}

	def testJenkins() {
		def projects = [
			"xserver",
			"partner_data_extractor",
			"spring",
			"apidoc",
			"alertlog"
		]
		def tags = ""
		for (i in projects) {
			tags += ("git ls-remote -t -h https://talksoft-dev:remindme123@github.com/RevSpring/tsoft_" + i).execute().text
		}

		def result = tags.readLines()
				.collect { it.split()[1].replaceAll('refs/heads/', '')  }
				.unique().findAll {
					!it.contains("tag")
				}

		result.remove("develop")
		result.remove("master")
		result.add(0, "master")
		result.add(1, "develop")
		return result;
	}
}
