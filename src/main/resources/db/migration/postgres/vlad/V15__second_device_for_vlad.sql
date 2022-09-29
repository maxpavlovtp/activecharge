DELETE
FROM public.station
WHERE number = '3';

DELETE
FROM public.station
WHERE number = '4';

UPDATE public.station
SET provider_device_id = '1001690629'
WHERE number = '2';

