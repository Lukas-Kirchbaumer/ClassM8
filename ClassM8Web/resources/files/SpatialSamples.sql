DROP TABLE matepoint CASCADE CONSTRAINTS;
CREATE TABLE matepoint (
	mid INTEGER PRIMARY KEY,
	shape SDO_GEOMETRY
);
INSERT INTO user_sdo_geom_metadata
( TABLE_NAME,
COLUMN_NAME,
DIMINFO,
SRID
)
VALUES
( 'matepoint',
'shape',
SDO_DIM_ARRAY( -- 1200x600 grid
SDO_DIM_ELEMENT('X', 0, 1200, 0.005),
SDO_DIM_ELEMENT('Y', 0, 600, 0.005)
),
NULL -- SRID
);

CREATE INDEX index_matepoint ON matepoint(shape) INDEXTYPE IS MDSYS.SPATIAL_INDEX;


INSERT INTO matepoint VALUES(
   1,
   SDO_GEOMETRY(
      2001,
      NULL,
      SDO_POINT_TYPE(1, 14, NULL),
      NULL,
      NULL));
      

      
SELECT m.shape.SDO_POINT.X from matepoint m;
