import re

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

