# Spring Transaction Propagations examples

## How is it used in example

- [PersonService.java](SpringTransactionPropagationExamples/src/main/java/com/vipul/service/PersonService.java) has different methods defined with propagations levels as NEVER, NOT_SUPPORTED, SUPPORTED, MANDATORY, REQUIRED, REQUIRES_NEW and NESTED.
- PersonService.java has one non-transactional method 'callTransactionalMethodsWithoutTrasaction()' calling all propagation methods to test the behavior when transaction is not present.
- PersonService.java has one transactional method 'callTransactionalMethodsWithTrasaction()' calling all propagation methods to test behavior when transaction is present.

## Importing and running project

1. Import project as Maven project
2. Run application as Spring Boot Application
3. SpringTransactionPropagationExamplesApplication.java](SpringTransactionPropagationExamples/src/main/java/com/vipul/SpringTransactionPropagationExamplesApplication.java) gets the PersonService bean and calls callTransactionalMethodsWithoutTrasaction() and callTransactionalMethodsWithTrasaction()
4. Observe log and relate it with PersonService logging statements.