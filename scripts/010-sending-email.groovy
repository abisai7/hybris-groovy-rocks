// Create a temporary file for the demo
file = File.createTempFile("stuff",".tmp")
file.deleteOnExit()
pw = new PrintWriter(file)
pw.println 'This is line 1'
pw.println 'This is line 2'
pw.close()

// Create the email
email = de.hybris.platform.util.mail.MailUtils.getPreConfiguredEmail()
email.addTo('r.aliev@gmail.com')
email.subject = 'Important stuff attached'
email.msg = 'Here is your attachment'

// Create an attachment that is our temporary file
attachment = new org.apache.commons.mail.EmailAttachment();
attachment.path = file.absolutePath
attachment.disposition = org.apache.commons.mail.EmailAttachment.ATTACHMENT
attachment.description = 'Stuff'
attachment.name = 'stuff.txt'

// Attach the attachment
email.attach(attachment)

// Send the email
email.send()

// Clean up
file.delete()