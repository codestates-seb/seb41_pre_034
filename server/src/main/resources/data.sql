insert into `users` (`display_name`, `email`, `email_notice`, `login_type`, `password`,`user_status`)
values
    ('testUser', 'test@test.com', 1, 'BASIC', '{noop}1111!', 'ACTIVITY'),
    ('testUser1', 'test1@test.com', 1, 'BASIC', '{noop}1111!', 'ACTIVITY'),
    ('testUser2', 'test2@test.com', 1, 'BASIC', '{noop}1111!', 'ACTIVITY'),
    ('testUser3', 'test3@test.com', 1, 'BASIC', '{noop}1111!', 'ACTIVITY'),
    ('testUser4', 'test4@test.com', 1, 'BASIC', '{noop}1111!', 'ACTIVITY'),
    ('testUser5', 'test5@test.com', 1, 'BASIC', '{noop}1111!', 'ACTIVITY')
;

insert into `questions` (`user_user_id`, `body`, `title`, `answer_counting`, `counting_vote`, `question_status`, `view_counting`, `tag_string`)
values
    (1, 'Test Body', 'Test Title', 2, 0, 'OPENED', 0, 'test'),
    (1, 'Test Body1', 'Test Title1', 0, 0, 'OPENED', 0, 'test'),
    (1, 'Test Body2', 'Test Title2', 0, 0, 'OPENED', 0, 'test'),
    (1, 'Test Body3', 'Test Title3', 0, 0, 'OPENED', 0, 'test'),
    (1, 'Test Body4', 'Test Title4', 0, 0, 'OPENED', 0, 'test'),
    (1, 'Test Body5', 'Test Title5', 0, 0, 'OPENED', 0, 'test'),
    (1, 'Test Body6', 'Test Title6', 0, 0, 'OPENED', 0, 'test'),
    (1, 'Test Body7', 'Test Title7', 0, 0, 'OPENED', 0, 'test'),
    (1, 'Test Body8', 'Test Title8', 0, 0, 'OPENED', 0, 'test'),
    (1, 'Test Body9', 'Test Title9', 0, 0, 'OPENED', 0, 'test'),
    (1, 'Test Body2', 'Test Title10', 0, 0, 'OPENED', 0, 'test'),
    (1, 'Test Body2', 'Test Title11', 0, 0, 'OPENED', 0, 'test'),
    (1, 'Test Body2', 'Test Title12', 0, 0, 'OPENED', 0, 'test'),
    (1, 'Test Body2', 'Test Title132', 0, 0, 'OPENED', 0, 'test'),
    (1, 'Test Body2', 'Test Title14', 0, 0, 'OPENED', 0, 'test'),
    (1, 'Test Body2', 'Test Title15', 0, 0, 'OPENED', 0, 'test'),
    (1, 'Test Body2', 'Test Title16', 0, 0, 'OPENED', 0, 'test'),
    (1, 'Test Body2', 'Test Title17', 0, 0, 'OPENED', 0, 'test'),
    (1, 'Test Body2', 'Test Title18', 0, 0, 'OPENED', 0, 'test'),
    (1, 'Test Body2', 'Test Title19', 0, 0, 'OPENED', 0, 'test'),
    (1, 'Test Body2', 'Test Title20', 0, 0, 'OPENED', 0, 'test'),
    (1, 'Test Body2', 'Test Title21', 0, 0, 'OPENED', 0, 'test'),
    (1, 'Test Body2', 'Test Title22', 0, 0, 'OPENED', 0, 'test'),
    (2, 'Test Body3', 'Test Title23', 0, 0, 'OPENED', 0, 'test'),
    (2, 'Test Body3', 'Test Title24', 0, 0, 'OPENED', 0, 'test'),
    (2, 'Test Body3', 'Test Title25', 0, 0, 'OPENED', 0, 'test'),
    (2, 'Test Body3', 'Test Title26', 0, 0, 'OPENED', 0, 'test'),
    (2, 'Test Body3', 'Test Title27', 0, 0, 'OPENED', 0, 'test'),
    (2, 'Test Body3', 'Test Title28', 0, 0, 'OPENED', 0, 'test'),
    (2, 'Test Body3', 'Test Title29', 0, 0, 'OPENED', 0, 'test'),
    (2, 'Test Body3', 'Test Title30', 0, 0, 'OPENED', 0, 'test'),
    (2, 'Test Body3', 'Test Title31', 0, 0, 'OPENED', 0, 'test'),
    (2, 'Test Body3', 'Test Title32', 0, 0, 'OPENED', 0, 'test'),
    (2, 'Test Body3', 'Test Title33', 0, 0, 'OPENED', 0, 'test'),
    (2, 'Test Body3', 'Test Title34', 0, 0, 'OPENED', 0, 'test'),
    (2, 'Test Body4', 'Test Title35', 0, 0, 'OPENED', 0, 'test')
;

insert into `tags` (`tag`, `description`)
values
    ('java', 'Java is a high-level object-oriented programming language. Use this tag when you re having problems using or understanding the language itself. This tag is frequently used alongside other tags for libraries and/or frameworks used by Java developers.'),
    ('python', 'Python is a multi-paradigm, dynamically typed, multi-purpose programming language. It is designed to be quick to learn, understand, and use, and enforces a clean and uniform syntax. Please note that Python 2 is officially out of support as of 2020-01-01. For version-specific Python questions, add the [python-2.7] or [python-3.x] tag. When using a Python variant (e.g. Jython, PyPy) or library (e.g. Pandas, NumPy), please include it in the tags.'),
    ('javascript', 'For questions about programming in ECMAScript (JavaScript/JS) and its different dialects/implementations (except for ActionScript). Keep in mind that JavaScript is NOT the same as Java! Include all labels that are relevant to your question; e.g., [node.js], [jQuery], [JSON], [ReactJS], [angular], [ember.js], [vue.js], [typescript], [svelte], etc.'),
    ('php', 'PHP is a widely used, open source, general-purpose, multi-paradigm, dynamically typed and interpreted scripting language designed initially for server-side web development. Use this tag for questions about programming in the PHP language.'),
    ('jquery', 'jQuery is a JavaScript library. Consider also adding the JavaScript tag. jQuery is a popular cross-browser JavaScript library that facilitates Document Object Model (DOM) traversal, event handling, animations and AJAX interactions by minimizing the discrepancies across browsers. A question tagged jQuery should be related to jQuery, so jQuery should be used by the code in question, and at least jQuery usage-related elements must be in the question.'),
    ('html', 'HTML (HyperText Markup Language) is the markup language for creating web pages and other information to be displayed in a web browser. Questions regarding HTML should include a minimal reproducible example and some idea of what you''re trying to achieve. This tag is rarely used alone and is often paired with [CSS] and [JavaScript].'),
    ('reactjs', 'React is a JavaScript library for building user interfaces. It uses a declarative, component-based paradigm and aims to be efficient and flexible.'),
    ('ios', 'iOS is the mobile operating system running on the Apple iPhone, iPod touch, and iPad. Use this tag [ios] for questions related to programming on the iOS platform. Use the related tags [objective-c] and [swift] for issues specific to those programming languages.'),
    ('mysql', 'MySQL is a free, open-source Relational Database Management System (RDBMS) that uses Structured Query Language (SQL). DO NOT USE this tag for other DBs such as SQL Server, SQLite etc. Those are different DBs that all use their own dialects of SQL to manage the data.'),
    ('android', 'Android is Google''s mobile operating system, used for programming or developing digital devices (Smartphones, Tablets, Automobiles, TVs, Wear, Glass, IoT). For topics related to Android, use Android-specific tags such as android-intent, android-activity, android-adapter, etc. For questions other than development or programming but related to the Android framework, use this link: https://android.stackexchange.com.'),
    ('c#', 'C# (pronounced "see sharp") is a high-level, statically typed, multi-paradigm programming language developed by Microsoft. C# code usually targets Microsoft''s .NET family of tools and run-times, which include .NET, .NET Framework, .NET MAUI, and Xamarin among others. Use this tag for questions about code written in C# or about C#''s formal specification.')
;

insert into `question_tag` (`question_question_id`, `tag_tag_id`)
values
    (1,1),(1,2),(1,3),(2,1),(2,2),(2,3),(3,4),(3,5),(3,6),(4,1),(4,2),(4,3),(5,4),(5,5),(5,6) ;

insert into `question_comment` (`comment`, `question_question_id`, `user_user_id`)
values
    ('Test Comment',1, 3),
    ('Test Comment',1, 4),
    ('Test Comment',2, 4),
    ('Test Comment',2, 5),
    ('Test Comment',3, 5),
    ('Test Comment',3, 2),
    ('Test Comment',4, 3),
    ('Test Comment',4, 1),
    ('Test Comment',5, 5),
    ('Test Comment',5, 6)
;

insert into `question_vote` (`vote_status`, `question_question_id`, `user_user_id`)
values
    ('UP', 1, 2),
    ('UP', 1, 3),
    ('UP', 1, 4),
    ('DOWN', 1, 5),
    ('UP', 1, 6),
    ('DOWN', 2, 1),
    ('UP', 2, 2),
    ('DOWN', 2, 3),
    ('UP', 3, 4),
    ('UP', 3, 5),
    ('UP', 3, 2),
    ('DOWN', 4, 2),
    ('UP', 4, 5),
    ('DOWN', 5, 3),
    ('UP', 5, 6)
;

insert into `answers` (`body`, `checked`, `question_question_id`, `user_user_id`, `counting_vote`)
values
    ('Test Body', 0, 1, 2, 0),
    ('Test Body', 0, 1, 3, 0),
    ('Test Body', 0, 1, 4, 0),
    ('Test Body', 0, 1, 3, 0),
    ('Test Body', 0, 1, 6, 0),
    ('Test Body', 0, 2, 1, 0),
    ('Test Body', 0, 3, 3, 0),
    ('Test Body', 0, 4, 4, 0),
    ('Test Body', 0, 5, 2, 0),
    ('Test Body', 0, 5, 1, 0)
;

insert into `answer_comment` (`comment`, `answer_answer_id`, `user_user_id`)
values
    ('Test Comment',1, 3),
    ('Test Comment',1, 4),
    ('Test Comment',2, 4),
    ('Test Comment',2, 5),
    ('Test Comment',3, 5),
    ('Test Comment',3, 2),
    ('Test Comment',4, 3),
    ('Test Comment',4, 1),
    ('Test Comment',5, 5),
    ('Test Comment',5, 6)
;

insert into `answer_vote` (`vote_status`, `answer_answer_id`, `user_user_id`)
values
    ('UP', 1, 2),
    ('UP', 1, 3),
    ('UP', 1, 4),
    ('DOWN', 1, 5),
    ('UP', 1, 6),
    ('DOWN', 2, 1),
    ('UP', 2, 2),
    ('DOWN', 2, 3),
    ('UP', 3, 4),
    ('UP', 3, 5),
    ('UP', 3, 2),
    ('DOWN', 4, 2),
    ('UP', 4, 5),
    ('DOWN', 5, 3),
    ('UP', 5, 6)
;

insert into `user_roles`(`user_user_id`, `roles`)
values
    (1, 'USER'),
    (2, 'USER'),
    (3, 'USER'),
    (4, 'USER'),
    (5, 'USER'),
    (6, 'USER')
;