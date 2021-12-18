create table bigmember(
	id varchar2(500), 
	pw varchar2(500),
	nick varchar2(500)
)

insert into bigmember
 values('pgh', '1234', 'geon');
insert into bigmember
 values('lsh', '5678', '½ÂÈ¯¿ÕÀÚ');
insert into bigmember
 values('pgh', '1234', 'µ¿¿øÂüÄ¡');
insert into bigmember
 values('csm', '1234', 'ÁË¼ö¹Î');
 
 

 select * from BIGMEMBER