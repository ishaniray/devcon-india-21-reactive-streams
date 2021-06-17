USE [devcon21]
CREATE TABLE Ratings (
    Id int IDENTITY(1000, 1) PRIMARY KEY,
	OrderId int,
	CustId int,
	RestaurantId int,
	DeliveryAgentId int,
	FoodRating int,
	DeliveryRating int
);


USE [devcon21]
GO
SET IDENTITY_INSERT [dbo].[Ratings] ON 
GO
INSERT [dbo].[Ratings] 
([Id], [OrderId], [CustId], [RestaurantId], [DeliveryAgentId], [FoodRating], [DeliveryRating]) 
VALUES (1000, 10000, 459, 245, 753, 4, 5)
GO
INSERT [dbo].[Ratings] 
([Id], [OrderId], [CustId], [RestaurantId], [DeliveryAgentId], [FoodRating], [DeliveryRating])
VALUES (1001, 10001, 845, 543, 135, 5, 5)
GO
INSERT [dbo].[Ratings] 
([Id], [OrderId], [CustId], [RestaurantId], [DeliveryAgentId], [FoodRating], [DeliveryRating])
VALUES (1002, 10004, 565, 1256, 825, 3, 4)
GO
SET IDENTITY_INSERT [dbo].[Ratings] OFF
GO
