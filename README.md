# My weekend trial of learning Vaadin 
demo-vaadin-crud


## Introduction

I had a small task to write “Hello World”-like application using Vaadin framework during a weekend. Before that weekend I knew very a little about Vaadin framework. I knew that Vaadin uses GTW components but basically that was all about my knowledge about Vaadin. 
So let’s look on pros and cons what I found about Vaadin during one weekend.

## List of Pros that I found interesting. 
1) First thing that I discover that Vaadin can work now with Web components. So now Vaadin has are more possibilities to extend.
2) Vaadin have a good lib [“TestBench”](https://vaadin.com/directory/component/vaadin-testbench) for integration test. As fas as I understand It can really save your time on testing web application.  
3) Asynchronous Updates from server using @Push annotation.
4) Last but not least is this. I am quite sure that Java developers can more easy create and maintain a web front-end on Java that on JavaScript. It sound obvious but I want to emphasize this.

## What I accomplished during that weekend.
At first I selected Vaadin 10 as most stable and functional release.
Initially I had a choose how to create my project. 
1) First option was a creation of project from the scratch using [Spring Initializr](https://start.spring.io/) or [Vaadin Strater](https://vaadin.com/start). After this I could copy and paste interesting code samples from other Vaadin projects or articles to my project.
2) Second option was made a fork from existing Vaadin project then understand how it works and add some new functionality to this project.

I chooses second option. I made a fork from [project demo-vaadin-crud](https://github.com/snicoll-demos/demo-vaadin-crud). Besides this project I recommend to [watch a presentation](https://www.youtube.com/watch?v=-BlJQqT8Kpg)  about this project and beyond it.  After watching of the presentation and some investigation of code everything inside project was a quite understandable for me. 
I added a component Tabs with two Tab: first tab with Vaadin java components and  second with Web component. As Web component I selected spreadsheet [Hot-Table](http://handsontable.github.io/hot-table/)
 because [Hot-Table](http://handsontable.github.io/hot-table/) has many functions and I was curios about using some of them. But on that weekend I manage only to show modification of data in [Hot-Table](http://handsontable.github.io/hot-table/). I also made small refactoring and divided classes on packages and it required small configuration of Spring. And of course I didn’t forget to write unit tests for my code.

## In my humble opinion list of Cons of Vaadin. 

I think that in general you can not work productively with Vaadin without paid license. 
For example TestBench required it for work.

It is true that Web Component community is larger than GWT  community. But It is not mainstream of front-end development.  In general JavaSript pure libraries is more popular.  For example if compare Hot-Table web component with his parent project Handsontable then you will see that  JavaScript library Handsontable is more powerful than web component. By the way nowdays Javascript family has many powerful features especially when you look on ES6 and TypeScript.  


## What I could not do during that weekend.
Besides Vaadin I also didn’t have experience of work with [Web components](https://www.webcomponents.org/). 
So as results I had to spent some time on investigation of [Web components](https://www.webcomponents.org/). 
By the way that research of Vaadin was more easy for me than [Web components](https://www.webcomponents.org/). 
May be because  Vaadin has more documentation than  [Web components](https://www.webcomponents.org/) in general and [Hot-Table](http://handsontable.github.io/hot-table/) in particular.
For example I wanted to use theme LUMO.DARK. But [Hot-Table](http://handsontable.github.io/hot-table/) looks ugly with this theme. When I tried to change styles I discovered that it is not so simple with web components. 
First advice for changing styles was to made fork from [Hot-Table](http://handsontable.github.io/hot-table/) and make changes of style inside this new fork. 
Second workaround for changing styles was changing styles on Shadow DOM  of [Hot-Table](http://handsontable.github.io/hot-table/).
But this advice is more appropriate for JavaScript than for Vaadin.
   
I planed to made CRUD operations for [Hot-Table](http://handsontable.github.io/hot-table/).But I did not found how to attach a event listener on Hot-table events. At least their site is silent on it. So during my time limit I could not found solution for this problem.

But what make me really unsatisfied is integration tests. I planed to use TestBench. But I simply could not run my integration test with my configuration of Spring Boot and Vaadin. By documentation everything is easy. And it should be easy. But it was not. I get this exception during running of integration test.
```org.apache.maven.plugin.MojoExecutionException: Spring application did not start before the configured timeout (30000ms'```
Inside source code of ```org.springframework.boot.maven.StartMojo.waitForSpringApplication``` 
I found that it waiting for JMX Mbean with name ```"org.springframework.boot:type=Admin,name=SpringApplication"```  But this Mbean is not created somehow by SpringApplication . I suspect it happened because of com.vaadin.flow.spring.SpringBootAutoConfiguration any case it was already beyond of my time limit because I spent too much time on this problem.  I even pointed directly to create this Mbean with key “spring.application.admin.enabled=true” but I broke other tests because this Mbean was already created inside this tests. 


## Conclusion

As outcome I can said that Vaadin will be very useful any Java Developer  who create or maintain  web application. Learning curve should be small. 
