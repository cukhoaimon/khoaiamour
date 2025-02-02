CREATE KEYSPACE IF NOT EXISTS khoaiamour
    WITH replication = {
        'class': 'NetworkTopologyStrategy',
        'replication_factor': 3
        };
