# Awesome New Games Store

- [Architecture, use cases diagrams](https://lucid.app/documents/embedded/96433f6b-1c33-42f8-83c4-fd91e4b9bf1d?invitationId=inv_89fc0898-9909-44bb-8504-364d9ff65afe#)


## About tests

 - *UT - Unit Test, eg. ProductStockUT - Unit Tests for Product Stock
 - *IT - Integration Test
 - *FT - Functional Test

Please browse to OrderingProductsFT as it demonstrates the whole required interaction between the caller and the service.

## Architecture Decision Record Log

### 1. Use Spring Boot/Java 17 as platform for application.
### 2. Develop service using Walking skeleton method to enable fast implementation of core logic without waiting for dependencies. 
1. Postpone any data layer integration as late as possible.
2. Postpone any service integration (via HTTP or MQ) as late as possible.

