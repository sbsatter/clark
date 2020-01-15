SELECT min(t.time), avg(t.time), max(t.time) from (select (TIMESTAMPDIFF(SECOND, orderedAt, orderFulfilledAt)) time from ProductOrder where type='order_fulfilled') t;

set @time= '2019-08-01 16:02:19.000000';
SELECT count(*)  from ProductOrder where orderedAt <= @time and lastUpdatedAt > @time;

