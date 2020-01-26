SELECT min(t.time), avg(t.time), max(t.time) from (select (TIMESTAMPDIFF(SECOND, orderedAt, orderFulfilledAt)) time from ProductOrder where type='order_fulfilled') t;

set @time= '2019-08-01 16:02:19.000000';
SELECT count(*)  from ProductOrder where orderedAt <= @time and lastUpdatedAt > @time;

-- Find probability of cancelled orders by age
select floor(DATEDIFF(p.orderedAt, c.birthDate) / 365) age, (COUNT(CASE WHEN p.type='order_cancelled' THEN 1 else NULL END)/count(p.type)) as ratio
from ProductOrder p
inner join Customer c on p.customerAggregateId = c.aggregateId
group by age
;
