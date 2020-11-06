USE [InventoryManagementSystem]
GO

INSERT INTO [dbo].[products] ([product_name], [product_description])
     VALUES
           ('ABC', 'Product ABC'),
		   ('DEF', 'Product DEF'),
		   ('GHI', 'Product GHI'),
		   ('LMN', 'ProductLMN'),
		   ('OPQ', 'Product OPQ')
GO


INSERT INTO [dbo].[inventory] ([product_id],[qty])
     VALUES
           (2,15000),
		   (3,11234),
		   (4,23423),
		   (5,31234),
		   (6,12334)
GO


USE [inventory]
GO

SELECT [product_id]
      ,[qty]
      ,[startTime]
      ,[endTime]
  FROM [dbo].[inventory]

GO

SELECT [product_id]
      ,[product_name]
      ,[product_description]
      ,[startTime]
      ,[endTime]
  FROM [dbo].[products]

GO


UPDATE [dbo].[inventory]
   SET [qty] = [qty] + 12003
 WHERE [product_id]=1
GO

