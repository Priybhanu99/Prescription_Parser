import re
import json
import pathlib

med_data = {
	"CID100000298":{
		"generic_name": "Singulair",
		"description": "Singulair is used to prevent asthma attacks in adults and children as young as 12 months old. Singulair is also used to prevent exercise-induced bronchoconstriction (narrowing of the air passages in the lungs) in adults and children who are at least 6 years old. Singulair is also used to treat symptoms of year-round (perennial) allergies in adults and children who are at least 6 months old. It is also used to treat symptoms of seasonal allergies in adults and children who are at least 2 years old",
		"side_effects": "anger, aggression, feeling restless or irritable\nagitation, anxiety,depression, confusion, problems with memory or attention\nhallucinations, sleep problems, strange dreams, sleep-walking",
		"price": 1000
	},
	"CID100000302":{
		"generic_name": "Atrovent",
		"description": "Atrovent is used to help control the symptoms of lung diseases, such as asthma, chronic bronchitis, and emphysema. It is also used to treat air flow blockage and prevent the worsening of chronic obstructive pulmonary disease (COPD). Ipratropium belongs to the family of medicines known as bronchodilators. Bronchodilators are medicines that are breathed in through the mouth to open up the bronchial tubes (air passages) in the lungs. This medicine is available only with your doctor's prescription.",
		"side_effects": "Bladder pain\nbloody or cloudy urine\ncough producing mucus\ndifficulty with breathing\nfrequent urge to urinate",
		"price": 1000
	},
	"CID100000305":{
		"generic_name": "Fortamet",
		"description": "Fortamet is an oral diabetes medicine that helps control blood sugar levels. Metformin is used together with diet and exercise to improve blood sugar control in adults with type 2 diabetes mellitus. Metformin is sometimes used together with insulin or other medications, but it is not for treating type 1 diabetes.",
		"side_effects":"unusual muscle pain\nfeeling cold\ntrouble breathing\nstomach pain, vomiting",
		"price": 1000
	},
	"CID100000410":{
		"generic_name": "Victoza",
		"description": "Victoza (liraglutide) is similar to a hormone that occurs naturally in the body and helps control blood sugar, insulin levels, and digestion. Victoza is used together with diet and exercise to improve blood sugar control in adults and children 10 years of age and older with type 2 diabetes mellitus. Victoza may also help reduce the risk of serious heart problems such as heart attack or stroke in adults with type 2 diabetes and heart disease. It is usually given after other diabetes medicines have been tried without success. Victoza is not for treating type 1 diabetes.",
		"side_effects":"racing or pounding heartbeats\nsevere ongoing nausea, vomiting, or diarrhea\nswelling or a lump in your neck, trouble swallowing, a hoarse voice, feeling short of breath\nfever, upper stomach pain, clay-colored stools, jaundice (yellowing of your skin or eyes)",
		"price": 1000
	},
	"CID100002217":{
		"generic_name": "Admelog",
		"description": "Admelog is used to improve blood sugar control in adults and children with diabetes mellitus. Admelog and HumaLOG are used to treat type 2 diabetes in adults, or type 1 diabetes in adults and children who are at least 3 years old.",
		"side_effects":"weight gain, swelling in your hands or feet, feeling short of breath\nlow blood sugar--headache, hunger, sweating, irritability, dizziness, fast heart rate, and feeling anxious or shaky\nirregular heartbeats, fluttering in your chest, increased thirst or urination, numbness or tingling, muscle weakness or limp feeling\nthickening or hollowing of the skin where you injected the medicine",
		"price": 1000
	},
	"CID100072232":{
		"generic_name": "Glucotrol",
		"description": "Glucotrol (glipizide) is an oral diabetes medicine that helps control blood sugar levels by helping your pancreas produce insulin. Glucotrol is used together with diet and exercise to improve blood sugar control in adults with type 2 diabetes mellitus. Glucotrol is not for treating type 1 diabetes.",
		"side_effects":"headache, irritability\nsweating, fast heart rate\ndizziness, nausea\ndiarrhea, constipation, gas",
		"price": 1000
	},
	"CID100430011":{
		"generic_name": "Epitol",
		"description": "Epitol is an anticonvulsant that is used to treat seizures and nerve pain such as trigeminal neuralgia and diabetic neuropathy. This medicine is also used to treat bipolar disorder.",
		"side_effects":"skin rash\nloss of appetite, right-sided upper stomach pain, dark urine\nslow, fast, or pounding heartbeats\nfever, chills, sore throat, mouth sores, bleeding gums, nosebleeds, pale skin, easy bruising, unusual tiredness, feeling light-headed or short of breath",
		"price": 1000
	},
	"CID105040291":{
		"generic_name": "Keppra",
		"description": "Keppra (levetiracetam) is an anti-epileptic drug, also called an anticonvulsant. Keppra is used to treat partial onset seizures in adults and children who are at least 1 month old. Keppra is also used to treat tonic-clonic seizures in people who are at least 6 years old, and myoclonic seizures in people who are at least 12 years old.",
		"side_effects":"unusual changes in mood or behavior\nconfusion, hallucinations, loss of balance or coordination\nextreme drowsiness, feeling very weak or tired\nproblems with walking or movement",
		"price": 1000
	},
	"CID100190816":{
		"generic_name": "Bystolic",
		"description": "Bystolic (nebivolol) belongs to a group of drugs called beta-blockers. Beta-blockers affect the heart and circulation (blood flow through arteries and veins). Bystolic is used to treat hypertension (high blood pressure). Lowering blood pressure may lower your risk of a stroke or heart attack.",
		"side_effects":"feeling short of breath, even with mild exertion\nswelling of your ankles or feet\nslow or uneven heartbeats\nnumbness or cold feeling in your hands and feet",
		"price": 1000
	},
	"CID100805336":{
		"generic_name": "Atenolol",
		"description": "Atenolol (Tenormin) is a beta-blocker that affects the heart and circulation (blood flow through arteries and veins). Atenolol is used to treat angina (chest pain) and hypertension (high blood pressure). Atenolol is also used to lower the risk of death after a heart attack.",
		"side_effects":"new or worsening chest pain\nslow or uneven heartbeats\na light-headed feeling, like you might pass out:shortness of breath (even with mild exertion), swelling, rapid weight gain",
		"price": 1000
	},
	"CID100069038":{
		"generic_name": "Benicar",
		"description": "Benicar (olmesartan) is an angiotensin II receptor antagonist. Olmesartan keeps blood vessels from narrowing, which lowers blood pressure and improves blood flow. Benicar is used to treat high blood pressure (hypertension) in adults and children who are at least 6 years old. Benicar is sometimes given together with other blood pressure medications.",
		"side_effects":"a light-headed feeling, like you might pass out\nlittle or no urination\nfast heart rate\nnausea, weakness, tingly feeling, chest pain, irregular heartbeats, loss of movement",
		"price": 1000
	}
}

def parse_mobile_number(phone_no):

	#Regular expression used to find phone numbers from the text extracted
	phone = re.findall(re.compile(r'(?:(?:\+?([1-9]|[0-9][0-9]|[0-9][0-9][0-9])\s*(?:[.-]\s*)?)?(?:\(\s*([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9])\s*\)|([0-9][1-9]|[0-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9]))\s*(?:[.-]\s*)?)?([2-9]1[02-9]|[2-9][02-9]1|[2-9][02-9]{2})\s*(?:[.-]\s*)?([0-9]{4})(?:\s*(?:#|x\.?|ext\.?|extension)\s*(\d+))?'), phone_no)

	if phone:
		number = ''.join(phone[0])
		if len(number) > 10:
			return '+' + number
		else:
			return number



def parse_email(email):

	#Regular expression used to find email from the text extracted from the prescription
	email = re.findall("([^@|\s]+@[^@]+\.[^@|\s]+)", email)

	if email:
		try:
			return email[0].split()[0].strip(';')
		except IndexError:
			return None

medicines = []
patient_data = {}
list_of_medicines = {}

def get_medicines():
    global medicines
    temp = ""
    for x in medicines:
        temp = temp + str(x) + " "
    return temp

def parse_medicines(text):

	global medicines
	global list_of_medicines
	global med_data
	medicines = re.findall("[C][I][D][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]", text)
	# print(medicines)

	# with open(str(pathlib.Path().absolute())+'med_data.json') as f:
	# 	med_data = json.load(f)



	for id in medicines:
	    if id in med_data:
		    list_of_medicines[id]=med_data[id]
	# print(list_of_medicines)

	# total_bill = calculate_bill()
	# print(total_bill)

def calculate_bill() :

	global list_of_medicines

	bill=0
	for id in list_of_medicines:
		bill = bill + list_of_medicines[id]["price"]
	return bill

def getName() :
	global patient_data
	return  patient_data["Name"]

def getEmail() :
	global patient_data
	return  patient_data["Email"]

def getPhone() :
	global patient_data
	return  patient_data["Phone"]

def getPrice() :
	global patient_data
	return  patient_data["Price"]


def print_medicinedetails(id):
    global list_of_medicines
    if id in list_of_medicines:
        return "true"
    else:
        return "false"

def get_med_name(id):
    global list_of_medicines
    return list_of_medicines[id]["generic_name"]

def get_med_description(id):
    global list_of_medicines
    return list_of_medicines[id]["description"]

def get_med_sideeffects(id):
    global list_of_medicines
    return list_of_medicines[id]["side_effects"]


def get_med_price(id):
    global list_of_medicines
    return list_of_medicines[id]["price"]


def prescription_parser(final_text):

	global medicines

	text = final_text.split('\n')

	prescription_text = []

	for i in text:
		if i != ' ' and i != '':
			prescription_text.append(i)

	prescription_text[0]=prescription_text[0][2:]
	patient_data["Name"] = "Prabhsimar Singh Taneja"
	patient_data["Phone"] = parse_mobile_number(final_text)
	patient_data["Email"] = parse_email(final_text)
	patient_data["Medicines"] = medicines
	total_bill = calculate_bill()
	patient_data["Price"] = total_bill
	# print(patient_data)
	# print(prescription_text)
	# print(final_text)
	return patient_data

def get_bill():
    global patient_data
    return patient_data["Price"]

def main(text_from_image):


	text_from_image = str(text_from_image)
	parse_medicines(text_from_image)
	ret = (prescription_parser(text_from_image))

	return ret