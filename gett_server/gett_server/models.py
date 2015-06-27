from django.db import models

# Create your models here.

# Table for Users
class GettUser(models.Model):
	first_name = models.CharField(max_length=30)
	last_name = models.CharField(max_length=30)
	email = models.EmailField(max_length=30)
	part_of_list = models.CharField(max_length=30)

	def __unicode__(self):
		return self.first_name

	def getResponseData(self):

		#Create Resposne Dictionary
		response_data = {}
		response_data["first_name"] = self.first_name
		response_data["last_name"] = self.last_name
		response_data["email"] = self.email
		response_data["user_id"] = self.id

		return response_data


class GettLists(models.Model):
	list_title = models.CharField(max_length=200)
	created_by_user = models.ForeignKey('CCUser', related_name="created_by_user")
	
	def __unicode__(self):
	    return self.project_title

	def getResponseData(self):

		#Create Resposne Dictionary
		response_data = {}
		response_data["list_title"] = self.list_title
		response_data["created_by_user"] = self.created_by_user.id
		
		return response_data

