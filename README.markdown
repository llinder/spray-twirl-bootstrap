## _"Spray - Twirl - Twitter Bootstrap"_ Template Project

This project is based on the [Spray Template project](https://github.com/spray/spray-template).
I added support for serving static files, added Twitter Bootstrap files and added Twirl for templating.
You can use this project as a template for a REST API or even serve a website.

Spray routing is split up in serveral traits for static routing, twirl pages and inline html.

>This project uses [Git Flow](https://github.com/nvie/gitflow).

Follow these steps to get started:

1. Git-clone this repository.

        $ git clone git@bitbucket.org:diversit/spray-twirl-bootstrap.git my-project

2. Change directory into your clone:

        $ cd my-project

3. Launch SBT:

        $ sbt

4. Compile everything and run all tests:

        > test
> Note: For the Selenium Chome test to be able to run, you need to install the [chromedriver](https://code.google.com/p/selenium/wiki/ChromeDriver) locally.
> Read my [blogpost](http://www.diversit.eu/2013/01/selenium-testing-with-scalatest.html) about how Selenium is setup for ScalaTest.
> And read the [ScalaTest Selenium documentation](http://www.scalatest.org/user_guide/using_selenium) for using Selenium in ScalaTests.

5. Start the application:

        > re-start

6. Browse to http://localhost:8080/

7. Start the application:

        > re-stop

8. Learn more at http://www.spray.io/ , https://github.com/spray/twirl and http://twitter.github.com/bootstrap/

9. Start hacking on `src/main/scala/com/example/MyService.scala`