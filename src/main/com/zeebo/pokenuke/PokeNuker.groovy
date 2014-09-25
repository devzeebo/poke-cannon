package com.zeebo.pokenuke

import com.gargoylesoftware.htmlunit.WebClient
import com.gargoylesoftware.htmlunit.html.HtmlForm
import com.gargoylesoftware.htmlunit.html.HtmlPage

/**
 * Created with IntelliJ IDEA.
 * User: Eric Siebeneich
 * Date: 9/24/14
 * Time: 6:46 PM
 * To change this template use File | Settings | File Templates.
 */
class PokeNuker {

	WebClient client

	PokeNuker(username, password) {
		client = new WebClient()
		HtmlPage page = client.getPage('https://m.facebook.com/pokes')

		HtmlForm form = page.getElementById('login_form')
		form.getInputByName('email').setValueAttribute(username)
		form.getInputByName('pass').setValueAttribute(password)

		page = form.getInputByName('login').click()

		loop()
	}

	def loop() {

		long minMillis = 1000
		long maxMillis = 60000
		long millis = 60000

		HtmlPage page

		while (true) {
			millis = client.getPage('https://m.facebook.com/pokes').getElementsByTagName('span').findAll {
				it.asText() == 'Poke Back'
			}*.click().size() ?
					Math.max((millis / 2) as long, minMillis) :
					Math.min(millis * 2, maxMillis)

			println millis

			sleep(millis)
		}
	}

	static def main(args) {
		println args
		new PokeNuker(args[0], args[1])
	}
}
