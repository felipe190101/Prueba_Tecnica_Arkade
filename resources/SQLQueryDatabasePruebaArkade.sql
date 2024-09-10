CREATE DATABASE Prueba_Arkade
GO


USE [Prueba_Arkade]
GO


CREATE TABLE [dbo].[Prealerta](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[nombre] [varchar](20) NOT NULL,
	[guia] [varchar](20) NOT NULL,
	[fecha_creacion] [datetime] NOT NULL,
	[estado] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[TipoEquipo](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[nombre] [varchar](20) NOT NULL,
	[largo_serial] [int] NOT NULL,
	[largo_mac] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[Equipo](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[serial] [varchar](20) NOT NULL,
	[mac] [varchar](20) NOT NULL,
	[observaciones] [varchar](255) NULL,
	[estado] [int] NOT NULL,
	[tipo_equipo] [int] NOT NULL,
	[prealerta] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO


ALTER TABLE [dbo].[Equipo]  WITH CHECK ADD FOREIGN KEY([prealerta])
REFERENCES [dbo].[Prealerta] ([id])
GO


ALTER TABLE [dbo].[Equipo]  WITH CHECK ADD FOREIGN KEY([tipo_equipo])
REFERENCES [dbo].[TipoEquipo] ([id])
GO


