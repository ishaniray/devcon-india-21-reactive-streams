# -*- coding: utf-8 -*-
"""
Created on Fri Jun  4 16:36:17 2021

@author: SS067504
"""


import pika
import time
from datetime import datetime
import itertools

def publish_msg(id):
    channel.basic_publish(exchange='', routing_key='processor', body=id)
    now = datetime.now()
    current_time = now.strftime("%H:%M:%S")
    print(str(current_time)+" - Published message to queue")
    
connection = pika.BlockingConnection(pika.ConnectionParameters(host='localhost'))
channel = connection.channel()

while True:
    for _ in itertools.repeat(None, 10):
        publish_msg('1000')
        publish_msg('1001')
        publish_msg('1002')
    time.sleep(10)
connection.close()
