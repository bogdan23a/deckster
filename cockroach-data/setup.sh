chmod +x /home/wait-for-it.sh
/home/wait-for-it.sh cockroach:26257 -- ./cockroach sql --insecure --host=cockroach < /home/setup.sql