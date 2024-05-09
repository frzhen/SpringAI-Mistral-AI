TEST INSTRUCTIONS
-----------------
## Setup:
### - Different Level should be able to run together in order: 
`junit.jupiter.testclass.order.default=org.junit.jupiter.api.ClassOrderer$OrderAnnotation`
#### junit-platform.properties:
```
junit.jupiter.execution.parallel.enabled = true;
junit.jupiter.execution.parallel.mode.default = same_thread|concurrent;
junit.jupiter.execution.parallel.mode.classes.default = same_thread|concurrent;
junit.jupiter.testclass.order.default=org.junit.jupiter.api.ClassOrderer$OrderAnnotation
```
### - Inside Test Class all tests methods need be in order: `@TestMethodOrder(MethodOrderer.OrderAnnotation.class)`

## 1. Unit Test:
    - Persistence and POJO level Test class-level Numbering: `1x`
    - Service level test class-level Numbering: `2x`
    - Controller or application level tests class-level Numbering: `3x`
## 2. Integration Test:
    - Application level integration Tests class-level Numbering: `4x`
    - System level interagtion tests class-level Numbering: `5x`
## 3. Module Level Application Tests:
    - Project level Application Tests class-level Numbering: `9x`
