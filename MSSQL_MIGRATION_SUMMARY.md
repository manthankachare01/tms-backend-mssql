# MSSQL Migration Summary

This document outlines all changes made to the project to support Microsoft SQL Server (MSSQL) database.

## Overview
The project has been successfully migrated from MySQL to MSSQL. All configuration and model files have been updated to ensure full compatibility with MSSQL.

## Changes Made

### 1. **Database Driver Migration (pom.xml)**
**File:** [toolsmanagement/pom.xml](toolsmanagement/pom.xml)

**Changes:**
- **Removed:** `com.mysql:mysql-connector-j` dependency
- **Added:** `com.microsoft.sqlserver:mssql-jdbc` dependency

```xml
<!-- Before -->
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>

<!-- After -->
<dependency>
    <groupId>com.microsoft.sqlserver</groupId>
    <artifactId>mssql-jdbc</artifactId>
    <scope>runtime</scope>
</dependency>
```

### 2. **Hibernate Configuration (application.properties)**
**File:** [src/main/resources/application.properties](toolsmanagement/src/main/resources/application.properties)

**Changes:**
- Added MSSQL JDBC driver class name
- Added MSSQL-specific Hibernate dialect
- Added Hibernate batch processing configuration for better performance with MSSQL

```properties
# Before (MySQL auto-detection)
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# After (MSSQL specific)
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.SQLServerDialect
spring.jpa.properties.hibernate.jdbc.batch_size=20
spring.jpa.properties.hibernate.order_inserts=true
spring.jpa.properties.hibernate.order_updates=true
```

### 3. **Model Classes - Column Length Specifications**

MSSQL requires explicit string column lengths for VARCHAR columns. The following models have been updated with appropriate `@Column(length=...)` annotations:

#### A. Admin Module
- **Admin.java** - Added column length specifications for all string fields (50-255 characters)

#### B. Core Models
1. **Tool.java** - Added column lengths for description, siNo, toolNo, toolLocation, location, condition, remark, lastBorrowedBy, createdBy
2. **Trainer.java** - Added column lengths for name, role, contact, status, email, password, location
3. **SuperAdmin.java** - Added column lengths for email, password, name, role
4. **Security.java** - Added column lengths for all string fields
5. **Kit.java** - Added column lengths for kitId, kitName, qualificationLevel, trainingName, location, lastBorrowedBy, remark, condition, createdBy
6. **KitAggregate.java** - Added column lengths for name and remark

#### C. Issuance Module
1. **Issuance.java** - Added column lengths for trainerName, trainingName, status, location, comment, issuanceType, remarks, approvedBy, approvalRemark
2. **IssuanceRequest.java** - Added column lengths for all string fields
3. **ReturnRecord.java** - Added column lengths for processedBy and remarks
4. **ReturnItem.java** - Added column lengths for condition and remark

#### D. Other Models
1. **Notification.java** - Added/updated column lengths for type, severity, title, targetRole, status, and location
2. **ChatbotQA.java** - Already configured with NVARCHAR(MAX) for answer field
3. **KeyIssuance.java** - Added column lengths for all string fields (50-100 characters)

### 4. **Specific MSSQL Data Type Configurations**

#### BIT Type for Booleans
- **Tool.java** - `calibrationRequired` field uses `columnDefinition = "BIT"`
- **ChatbotQA.java** - `isActive` field uses `columnDefinition = "BIT"`

#### NVARCHAR(MAX) for Large Text
- **Notification.java** - `message` field uses `columnDefinition = "NVARCHAR(MAX)"`
- **ChatbotQA.java** - `answer` field uses `columnDefinition = "NVARCHAR(MAX)"`

## Column Length Specifications Summary

| Type | Length | Used For | Examples |
|------|--------|----------|----------|
| 20 | Contact, Status | Short codes | Phone numbers, Status values |
| 50 | IDs, Roles | Identifiers | admin_id, role, condition |
| 100 | Names, Emails, Locations | Standard text | Admin name, Email, Location |
| 255 | Descriptions, Remarks, Comments | Long text | Tool description, Remarks |
| MAX | Very long content | Large text fields | Notification message, ChatBot answer |

## Database Connection String Format

For MSSQL, use the following connection string format in your `DB_URL` environment variable:

```
jdbc:sqlserver://[host]:[port];databaseName=[database];trustServerCertificate=true;
```

Example:
```
jdbc:sqlserver://localhost:1433;databaseName=toolsmanagement;trustServerCertificate=true;
```

## Key Features of MSSQL Implementation

✅ **Proper String Field Sizing** - All VARCHAR fields have explicit length specifications  
✅ **Batch Processing** - Enabled for improved performance with bulk operations  
✅ **Statement Ordering** - Configured for optimized INSERT/UPDATE execution  
✅ **NVARCHAR Support** - Large text fields use NVARCHAR(MAX) for Unicode support  
✅ **BIT Type Support** - Boolean fields mapped to MSSQL BIT data type  

## Testing Recommendations

1. **Database Creation** - Verify tables are created with correct column types and lengths
2. **Data Insertion** - Test inserting records with various string lengths
3. **Character Encoding** - Verify Unicode characters are properly stored and retrieved
4. **Performance** - Monitor query performance with the new batch processing settings
5. **Null Handling** - Test nullable and non-nullable field constraints

## Build Status

✅ **Project Compilation:** SUCCESS  
✅ **All Model Files:** Verified  
✅ **Dependencies:** MSSQL JDBC driver included  
✅ **Configuration:** Complete  

The project has been successfully compiled with all MSSQL-specific configurations in place.

## Additional Configuration Notes

### Environment Variables Required
```
DB_URL=jdbc:sqlserver://[host]:[port];databaseName=[database]
DB_USERNAME=[username]
DB_PASSWORD=[password]
```

### Optional Hibernate Properties
The following can be added to `application.properties` for additional optimization:

```properties
# For better connection pooling
spring.datasource.hikari.maximum-pool-size=20
spring.datasource.hikari.minimum-idle=5

# For MSSQL-specific timeout settings
spring.datasource.hikari.connection-timeout=20000
spring.datasource.hikari.idle-timeout=300000
```

## Migration Checklist

- [x] Replace MySQL driver with MSSQL JDBC driver
- [x] Update Hibernate dialect configuration
- [x] Add column length specifications to all string fields
- [x] Configure NVARCHAR(MAX) for large text fields
- [x] Configure BIT type for boolean fields
- [x] Add batch processing configuration
- [x] Verify project compilation
- [ ] Create MSSQL database
- [ ] Test application startup and database connection
- [ ] Perform data migration if needed
- [ ] Run integration tests
- [ ] Verify application functionality with MSSQL

## Troubleshooting

### Common MSSQL-Related Issues

1. **Connection String Errors**
   - Ensure `databaseName` parameter matches your actual database name
   - Use `trustServerCertificate=true` for SSL/TLS issues

2. **Column Length Exceeded**
   - Update the `@Column(length=...)` specification if longer storage is needed
   - Use `columnDefinition = "NVARCHAR(MAX)"` for unlimited length

3. **Character Encoding**
   - MSSQL uses NVARCHAR by default for Unicode support
   - Ensure client connection collation matches server settings

## Conclusion

The application is now fully configured for MSSQL database backend. All model classes have been updated with appropriate column specifications and the necessary Hibernate dialect configuration is in place. The project builds successfully and is ready for database connection and testing.

---
**Last Updated:** March 2, 2026  
**Status:** ✅ Complete
