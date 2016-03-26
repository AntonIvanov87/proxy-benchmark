#!/bin/sh
createdb proxy-benchmark
createuser -P proxy-benchmark
psql -h localhost -U proxy-benchmark < create-tables.sql