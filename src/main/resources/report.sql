SELECT min(t.time), avg(t.time), max(t.time) from (select (TIMESTAMPDIFF(SECOND, orderedAt, orderFulfilledAt)) time from ProductOrder where type='order_fulfilled') t;

set @time= '2019-08-01 16:02:19.000000';
SELECT count(*)  from ProductOrder where orderedAt <= @time and lastUpdatedAt > @time;

select floor(datediff(p.orderedAt, c.birthDate)/365) as age, (count(c.name) * 100)/(select count(*) from ProductOrder p1 where p1.type='order_cancelled') ratio from ProductOrder p
inner join Customer c on c.aggregateId = p.customer_aggregateId
where type = 'order_cancelled'
group by age order by age asc
;
